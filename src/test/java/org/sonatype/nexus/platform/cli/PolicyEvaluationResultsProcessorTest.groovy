/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://links.sonatype.com/products/nexus/attributions.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.nexus.platform.cli

import spock.lang.Specification

class PolicyEvaluationResultsProcessorTest
extends Specification
{
  def 'test args'() {
    setup:
      String[] args = ['-R', '/opt/sonatype/out/results.txt']

    when:
      PolicyEvaluationResultsProcessor.main(args)

    then:
      true
  }
}
