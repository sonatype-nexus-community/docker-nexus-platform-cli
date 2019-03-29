/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://links.sonatype.com/products/nexus/attributions.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.nexus.platform.cli

import spock.lang.Specification

class CommandLineOptionFilterTest
    extends Specification
{
  def 'not letting help option thru'() {
    setup:
      String[] params = ['-h', '-a', 'auth', '-i', 'appId', '--help', '-s', 'serverUrl', 'dir1', 'file1', '-h']

    when:
      def args = CommandLineOptionFilter.filterCommandLineParams(params)

    then:
      !args.contains('-h')
      !args.contains('--help')
      args.contains('-a')
      args.contains('auth')
      args.contains('-i')
      args.contains('appId')
      args.contains('-s')
      args.contains('serverUrl')
      args.contains('dir1')
      args.contains('file1')
  }

  def 'test shortened param names'() {
    when:
      def params = [param]
      def args = CommandLineOptionFilter.filterCommandLineParams(params)

    then:
      args.contains(expected)

    where:
      param              | expected
      '-a'               | '-a'
      '-s'               | '-s'
      '-i'               | '-i'
      '--authorization'  | '-a'
      '--server-url'     | '-s'
      '--application-id' | '-i'
  }
}
