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
  if (cliBuild.isFeatureBuild()) {
    sh "echo host: https://circleci.com > ~/.circleci/cli.yml"
    sh "echo endpoint: graphql-unstable >> ~/.circleci/cli.yml"
    withCredentials([string(credentialsId: 'circleci-orbtest', variable: 'TOKEN')]) {
      sh "echo token: ${TOKEN} >> ~/.circleci/cli.yml"
      sh "${env.WORKSPACE}/circleci/circleci orb publish orb.yml orbtest/circleci-nexus-orb@dev:${env.BRANCH_NAME}"
    }
  }
  else {
    withCredentials([string(credentialsId: 'circleci-deployment-token', variable: 'TOKEN')]) {
      sh "echo token: ${TOKEN} >> ~/.circleci/cli.yml"
      sh "${env.WORKSPACE}/circleci/circleci orb publish increment orb.yml sonatype/nexus-platform-orb patch"
    }
  }
}
