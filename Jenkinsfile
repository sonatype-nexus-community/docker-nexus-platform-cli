/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://links.sonatype.com/products/nexus/attributions.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */

@Library(['docker-nexus-platform-cli-pipeline-library@INT-1376_create_jenkinsfile','jenkins-shared']) _

withEnv(['KEY=value']) {
  withCredentials([
      [$class: 'UsernamePasswordMultiBinding', credentialsId: 'docker-hub-credentials',
       usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD']
  ]) {
    cliPipeline()
  }
}