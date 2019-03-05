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
  if (cliBuild.isCIBuild()) {
    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: credentialsId,
                      usernameVariable: 'GITHUB_API_USERNAME', passwordVariable: 'GITHUB_API_PASSWORD']]) {
      sh "git tag \"${version}\""
      sh """
          git push \
          https://${env.GITHUB_API_USERNAME}:${env.GITHUB_API_PASSWORD}@github.com/sonatype/docker-nexus-platform-cli.git \
            ${version}
        """
    }
  }
}
