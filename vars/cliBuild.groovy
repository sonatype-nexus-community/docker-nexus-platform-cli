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

String getCommitId() {
  return sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
}

String getVersion() {
  def commitDate = runQuiet("git show -s --format=%cd --date=format:%Y%m%d-%H%M%S ${commitId}")

  def pom = readMavenPom(file: 'pom.xml')
  return pom.version.replace("-SNAPSHOT", ".${commitDate}.${commitId.substring(0, 7)}").trim()
}


boolean isFeatureBuild() {
  return currentBuild.fullProjectName ==~ /integrations\/cloud\/docker-nexus-platform-cli-feature\/.*/
}

boolean isCIBuild() {
  return currentBuild.fullProjectName == 'integrations/cloud/docker-nexus-platform-cli'
}

def printEnvironment() {
  sh 'env'
}

def runQuiet(String command) {
  def result
  try {
    result = sh(script: command, returnStdout: true)
    return result.trim()
  }
  catch (Exception e) {
    print result
    throw e
  }
}
