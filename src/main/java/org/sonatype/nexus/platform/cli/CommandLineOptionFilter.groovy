/*
 * Copyright (c) 2017-present Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://links.sonatype.com/products/nexus/attributions.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.nexus.platform.cli

import groovy.cli.commons.CliBuilder

class CommandLineOptionFilter
{
  // these are options that we don't want propagated to the IQ server CLI itself
  private static final String[] IGNORE_OPTIONS = ['-h', '--help', '--pki-authentication']

  private static final int EXIT_NO_PARAMS = -2

  private static final int EXIT_INSUFFICIENT_PARAMS = -3

  static String[] filterCommandLineParams(String[] params) {
    // we need to filter which options make it thru to the IQ CLI to prevent the IQ usage message from displaying
    def cli = new CliBuilder(
        usage: 'sh evaluate [options] [Archives or directories to scan]',
        header: 'Available options:')
    cli.with
        {
          s(longOpt: 'server-url', 'URL to the IQ Server to which the scan result should be uploaded', args: 1, required: true)
          i(longOpt: 'application-id', 'ID of the application on the IQ Server', args: 1, required: true)
          t(longOpt: 'stage',
              'The stage to run analysis against.  Accepted values: develop|build|stage-release|release|operate (Default: build)',
              args: 1)
          a(longOpt: 'authentication', 'Authentication credentials to use for the IQ Server, format <username:password>',
              args: 1)
          X(longOpt: 'debug',
              'Enable debug logs.  WARNING: This may expose sensitive information in the logs (Default: false)')
          xc(longOpt: 'expanded-coverage', 'Enable Expanded Coverage analysis (Default: false)')
          w(longOpt: 'fail-on-policy-warnings', 'Fail on policy evaluation warnings (Default: false)')
          e(longOpt: 'ignore-system-errors', 'Ignore system errors (IO, network, server, etc.) (Default: false)')
          // pki-authentication not supported initially as it will require additional work that is currently
          // out of scope for this initial effort, but leaving here for completeness
          //_(longOpt: 'pki-authentication', 'Delegate to the JVM for PKI authentication (Default: false)')
          p(longOpt: 'proxy',
              'Proxy to use, format <host{:port]>.  If unspecified, the operating system will be queried for the proxy settings',
              args: 1)
          U(longOpt: 'proxy-user', 'Credentials to use for the proxy, format <username:password>', args: 1)
          r(longOpt: 'result-file',
              'Path to a JSON file where the results of the policy evaluation will be stored in a machine-readable format',
              args: 1)
          h(longOpt: 'help', 'Show this help screen (Default: false)', required: false)
        }

    def options = cli.parse(params)
    if (!options) {
      System.exit(EXIT_NO_PARAMS)
    }
    if (options.h) {
      cli.usage()
    }

    String[] args = []
    // app ID, server URL and scan targets (i.e. options.arguments) are required
    if (options.i && options.s && options.arguments()) {
      args += ifPresentWithValue('-i', options.i)
      args += ifPresentWithValue('-s', options.s)
      args += ifPresentWithValue('-a', options.a)
      args += ifPresentWithValue('-t', options.t)
      args += ifPresentWithValue('-p', options.p)
      args += ifPresentWithValue('-U', options.U)
      args += ifPresentWithValue('-r', options.r)
      args += ifPresent('-e', options.e)
      args += ifPresent('-w', options.w)
      args += ifPresent('-xc', options.xc)
      args += ifPresent('-X', options.X)

      for (def arg : options.arguments()) {
        if (!IGNORE_OPTIONS.contains(arg)) {
          args += arg
        }
      }
    } else {
      if (!options.h) {
        cli.usage()
      }
      System.exit(EXIT_INSUFFICIENT_PARAMS)
    }

    System.out.println(args)

    return args
  }

  private static ifPresentWithValue(def name, def option) {
    return option ? [name, option] : []
  }

  private static ifPresent(def name, def option) {
    return option ? [name] : []
  }
}
