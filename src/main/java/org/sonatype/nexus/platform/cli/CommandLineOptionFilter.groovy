/*
 * Copyright (c) 2017-present Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://links.sonatype.com/products/nexus/attributions.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.nexus.platform.cli

import groovy.cli.commons.CliBuilder

class CommandLineOptionFilter
{
  static String[] filterCommandLineParams(String[] params) {
    // we need to filter which options make it thru to the IQ CLI to prevent the IQ usage message from displaying
    def cli = new CliBuilder(
        usage: 'sh evaluate [options] [Archives or directories to scan]',
        header: 'Available options:')
    cli.with
        {
          h(longOpt: 'help', 'Show this help screen', required: false)
          i(longOpt: 'application-id', 'ID of the application on the IQ Server', args: 1, required: true)
          a(longOpt: 'authentication', 'Authentication credentials to use for the IQ Server, format <username:password>',
              args: 1)
          s(longOpt: 'server-url', 'URL to the IQ Server to which the scan result should be uploaded', required: true,
              args: 1)
        }

    def options = cli.parse(params)
    if (!options) {
      System.exit(-2)
    }
    if (options.h) {
      cli.usage()
    }

    String[] args = []
    if (options.i && options.s && options.arguments()) {
      if (options.i) {
        args += ['-i', options.i]
      }
      if (options.s) {
        args += ['-s', options.s]
      }
      if (options.a) {
        args += ['-a', options.a]
      }

      for (def arg : options.arguments()) {
        if (!['-h', '--help'].contains(arg)) {
          args += arg
        }
      }
    } else {
      if (!options.h) {
        cli.usage()
      }
      System.exit(-3)
    }

    System.out.println(args)

    return args
  }
}
