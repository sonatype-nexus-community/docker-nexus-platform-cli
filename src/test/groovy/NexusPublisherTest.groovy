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


import org.testcontainers.containers.FixedHostPortGenericContainer
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll


@Testcontainers
class NexusPublisherTest
    extends Specification
{
  @Shared
  GenericContainer nexus = new FixedHostPortGenericContainer('sonatype/nexus3')
      .withFixedExposedPort(8081, 8081)
      .waitingFor(new HttpWaitStrategy().forPath('/').forStatusCode(200).forPort(8081))

  @Unroll
  def 'upload artifact to nexus using CLI parameters'() {
    when:
      def script = new File('src/main/groovy/NexusPublisher.groovy').text
      Eval.me('args',
          ["--username=${username}", "--password=${password}",
           "--serverurl=${serverUrl}", "--filename=${filename}", "--format=${format}",
           "--repository=${repository}"] + attributes, script)

    then:
      new URL("${serverUrl}repository/maven-releases/com/example/example/1.0/example-1.0.jar")
          .openConnection().contentLengthLong == (new File(filename)).length()

    where:
      username = 'admin'
      password = 'admin123'
      serverUrl = "http://${nexus.containerIpAddress}:8081/"
      filename = 'src/test/resources/example-1.0.jar'
      format = 'maven2'

      repository | attributes
      'maven-releases' | ['-CgroupId=com.example', '-CartifactId=example', '-Cversion=1.0', '-Aextension=jar']
  }
}
