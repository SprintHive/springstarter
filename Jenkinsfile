#!/usr/bin/groovy
@Library('github.com/SprintHive/sprinthive-pipeline-library@c2c6ac4590794943d7d058ee99ffce11fda281c3')

def componentName = 'spring-starter'
def versionTag = ''
def resourcesDir = 'config/kubernetes'
def dockerImage

gradleNode(label: 'gradle-and-docker') {
    stage('Compile source') {
        checkout scm
        versionTag = getNewVersion {}
        dockerImage = "${componentName}:${versionTag}"

        container(name: 'gradle') {
            sh "gradle bootRepackage"
        }
    }

    stage('Publish docker image') {
        container('docker') {
            sh "docker build -t ${dockerImage} ."
        }
    }

    stage('Rollout to Local') {
        def namespace = 'local'
        def deployStage = 'development'

        def kubeResources = kubeResourcesFromTemplates {
            templates = [
                readFile(resourcesDir + '/deployment.yaml'),
                readFile(resourcesDir + '/service.yaml')
            ]
            stage = deployStage
            version = versionTag
            image = dockerImage
            name = componentName
        }

        for (String kubeResource : kubeResources) {
            kubernetesApply(file: kubeResource, environment: namespace)
        }
    }
}
