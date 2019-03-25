#!/bin/sh
#
# Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
# Includes the third-party code listed at http://links.sonatype.com/products/nexus/attributions.
# "Sonatype" is a trademark of Sonatype, Inc.
#

groovy ${SONATYPE_DIR}/bin/NexusPolicyEvaluator.groovy

groovy ${SONATYPE_DIR}/bin/jar:file:nexus-platform-cli.jar'!'/org/sonatype/nexus/platform/cli/NexusPolicyEvaluator.groovy $@

# http://docs.groovy-lang.org/docs/groovy-2.5.1/html/gapi/groovy/cli/commons/CliBuilder.html
# https://github.com/spotify/dockerfile-maven