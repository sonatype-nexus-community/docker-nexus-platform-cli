/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://links.sonatype.com/products/nexus/attributions.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.nexus.platform.cli

import groovy.cli.commons.CliBuilder

class PolicyEvaluationResultsProcessor
{
  static void main(String[] args) {
    System.out.println(args)

    def cli = new CliBuilder()
    cli.R(type: String, longOpt: 'results', 'results file', args: 1, required: true)

    def options = cli.parse(args)
    if (!options || !options.R) {
      System.exit(-2)
    }
    def data = new File(options.R).getText('UTF-8')
    def isError = false
    data.eachLine {
      isError = it.startsWith('[ERROR]')
    }
    System.out.println('result = ' + isError)
  }
}
