/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */

def call() {
  withCredentials([
      [$class: 'UsernamePasswordMultiBinding', credentialsId: 'docker-hub-credentials',
       usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD']
  ]) {
    sh "echo ${env.DOCKERHUB_PASSWORD} | docker login --username ${env.DOCKERHUB_USERNAME} --password-stdin"
    if (cliBuild.isFeatureBuild()) {
      sh "docker tag sonatype/nexus-platform-cli:${env.VERSION} sonatype/nexus-platform-cli:dev-${env.BRANCH_NAME}"
      sh "docker push sonatype/nexus-platform-cli:dev-${env.BRANCH_NAME}"
    } else {
      sh "docker push sonatype/nexus-platform-cli:${env.VERSION}"
      sh "docker push sonatype/nexus-platform-cli:latest"
    }
  }
}
