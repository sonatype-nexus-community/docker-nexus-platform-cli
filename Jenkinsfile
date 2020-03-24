/*
 * Copyright (c) 2011-present Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://links.sonatype.com/products/clm/attributions.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
@Library(['private-pipeline-library', 'jenkins-shared', 'int-jenkins-shared']) _

String appId = 'docker-nexus-platform-cli'
String imageId = 'nexus-platform-cli'

boolean releaseBuild = (currentBuild.fullProjectName =~ /.*\/release$/)
echo "releaseBuild = $releaseBuild for ${currentBuild.fullProjectName}"

withCredentials([usernamePassword(credentialsId: 'zion-agent', passwordVariable: 'ZION_PASSWORD',
    usernameVariable: 'ZION_USER')]) {

  withCredentials([[$class          : 'UsernamePasswordMultiBinding', credentialsId: 'docker-hub-credentials',
                    usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD']]) {

    def mavenOptions = ''
    def postActions = {}
    def prepareActions = {}

    if (releaseBuild) {
      prepareActions = {
        sh "docker login --username ${DOCKERHUB_USERNAME} --password ${DOCKERHUB_PASSWORD} docker.io"
        stash name: 'readme', includes: 'README.md'
      }
      postActions = {
        if (currentBuild.resultIsBetterOrEqualTo('SUCCESS')) {
          dir('target/dist') {
            unstash 'readme'
          }
          sh "docker push sonatype/${imageId}"
          publishDockerProjectDescription(imageId)
        }
      }
    }
    else {
      mavenOptions = "-Ddockerfile.username=$ZION_USER -Ddockerfile.password=$ZION_PASSWORD"

      postActions = {
        if (currentBuild.resultIsBetterOrEqualTo('SUCCESS')) {
          sh "docker push ${sonatypeDockerRegistryId()}/sonatype/dev-${env.BRANCH_NAME}"
        }
      }
    }

    def pipelineConfig = [
        prepare             : prepareActions,
        useEventSpy         : false,
        javaVersion         : 'Java 8',
        mavenVersion        : 'Maven 3.5.x',
        mavenOptions        : mavenOptions,
        iqPolicyEvaluation  : { stage ->
          nexusPolicyEvaluation iqStage: stage, iqApplication: appId,
              iqScanPatterns: [[scanPattern: 'none-use_component_index']],
              failBuildOnNetworkError: true
        },
        distFiles           : [
            includes: [
                'target/*.jar*'
            ],
            excludes: [
                '**/*-sources.jar*',
                '**/*-javadoc.jar*'
            ]
        ],
        additionalArtifacts : 'target/spock-reports/**/*',
        performSonarAnalysis: true,
        sonarAnalysisPullRequestsOnly: currentBuild.projectName != 'master-snapshot',
        testResults         : ['**/target/*-reports/*.xml'],
        onSuccess           : postActions,
        onFailure           : postActions,
        jira                : [versionPrefix: appId]
    ]

    withSonatypeDockerRegistry() {
      make(pipelineConfig)
    }
  }
}
