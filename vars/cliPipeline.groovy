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
  pipeline {
    agent {
      label 'ubuntu-zion'
    }
    environment {
      VERSION = cliBuild.getVersion()
      COMMIT_ID = cliBuild.getCommitId()
    }
    triggers {
      // every 4 minutes monday - friday
      pollSCM(cliBuild.isCIBuild() ? 'H */4 * * 1-5' : '')
    }
    stages {
      stage('Preparation') {
        steps {
          cliPreparation()
        }
      }
      stage('Build') {
        steps {
          cliDockerBuild()
        }
      }
      stage('Archive') {
        steps {
          cliArchive()
        }
      }
      stage('Push') {
        steps {
          cliDockerPush()
        }
      }
      stage('GithubTag') {
        steps {
          cliGithubTag()
        }
      }
    }
  }
}
