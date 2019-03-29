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
  def cli

  def 'setup'() {
    cli = new CliBuilder()
    cli.with {
      h(longOpt: 'help', '')
      a(longOpt: 'authentication', '', args: 1)
      i(longOpt: 'application-id', '', args: 1)
      s(longOpt: 'server-url', '', args: 1)
      e(longOpt: 'ignore-system-errors', '')
      r(longOpt: 'result-file', '', args: 1)
      t(longOpt: 'stage', '', args: 1)
      X(longOpt: 'debug', '')
      xc(longOpt: 'expanded-coverage', '')
      w(longOpt: 'fail-on-policy-warnings', '')
      p(longOpt: 'proxy', '', args: 1)
      U(longOpt: 'proxy-user', '', args: 1)
      _(longOpt: 'pki-authentication', '')
    }
  }

  def 'not letting help and pki options thru'() {
    setup:
      String[] params = [
          '-h',
          '-a', 'auth',
          '-i', 'appId',
          '-s', 'serverUrl',
          '-e',
          '-t', 'develop',
          '-X',
          '-xc',
          '-w',
          '--pki-authentication',
          '-p', 'proxyUser',
          '-U', 'proxy:creds',
          '-r', 'out.json',
          'dir1', 'file1', '-h'
      ]

    when:
      def args = CommandLineOptionFilter.filterCommandLineParams(params)
      def options = cli.parse(args)

    then:
      !options.h
      !options.'help'
      !args.contains('-h')
      !args.contains('--help')
      args.contains('-a')
      options.a == 'auth'
      args.contains('-i')
      options.i == 'appId'
      args.contains('-s')
      options.s == 'serverUrl'
      args.contains('-e')
      args.contains('-t')
      options.t == 'develop'
      args.contains('-X')
      args.contains('-xc')
      args.contains('-w')
      !args.contains('--pki-authentication')
      args.contains('-p')
      options.p == 'proxyUser'
      args.contains('-U')
      options.U == 'proxy:creds'
      args.contains('-r')
      options.r == 'out.json'
  }

  def 'test shortened param names'() {
    setup:
      String[] params = [
          '--help',
          '--authentication', 'u:p',
          '--server-url', 'http://myServer',
          '--application-id', 'myApp',
          '--ignore-system-errors',
          '--result-file', 'file.json',
          '--stage', 'develop',
          '--debug',
          '--expanded-coverage',
          '--fail-on-policy-warnings',
          '--proxy', 'myProxy',
          '--proxy-user', 'user:pass',
          '--pki-authentication',
          'myArtifact.jar'
      ]

    when:
      def args = CommandLineOptionFilter.filterCommandLineParams(params)
      def options = cli.parse(args)

    then:
      args.contains('-a')
      options.a == 'u:p'
      args.contains('-i')
      options.i == 'myApp'
      args.contains('-s')
      options.s == 'http://myServer'
      args.contains('-e')
      args.contains('-r')
      options.r == 'file.json'
      args.contains('-t')
      options.t == 'develop'
      args.contains('-X')
      args.contains('-xc')
      args.contains('-w')
      args.contains('-p')
      options.p == 'myProxy'
      args.contains('-U')
      options.U == 'user:pass'
      !args.contains('--pki-authentication')
      !args.contains('-h')
      !args.contains('--help')
      !args.contains('--authentication')
      !args.contains('--application-id')
      !args.contains('--server-url')
      !args.contains('--ignore-system-errors')
      !args.contains('--result-file')
      !args.contains('--stage')
      !args.contains('--debug')
      !args.contains('--expanded-coverage')
      !args.contains('--fail-on-policy-warnings')
      !args.contains('--proxy')
      !args.contains('--proxy-user')
  }
}
