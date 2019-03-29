/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://links.sonatype.com/products/nexus/attributions.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.nexus.platform.cli

import com.sonatype.insight.scan.cli.PolicyEvaluatorCli

import static org.sonatype.nexus.platform.cli.CommandLineOptionFilter.filterCommandLineParams

class NexusPolicyEvaluator
{
  static void main(String[] args) {
    PolicyEvaluatorCli.main(filterCommandLineParams(args));
  }

}
