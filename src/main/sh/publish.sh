#!/bin/sh
#
# Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
# Includes the third-party code listed at http://links.sonatype.com/products/nexus/attributions.
# "Sonatype" is a trademark of Sonatype, Inc.
#

groovy ${SONATYPE_DIR}/bin/NexusPublisher.groovy $@
