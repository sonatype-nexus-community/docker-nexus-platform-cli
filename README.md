##Sonatype docker-nexus-platform-cli

####Nexus Policy Evaluation

    Usage: sh evaluate [options] <Archives or directories to scan>

    Options:
    
    -i, --application-id
      ID of the application on the IQ Server
    -a, --authentication
      Authentication credentials to use for the IQ Server, format <username:password>
    -X, --debug
      Enable debug logs. WARNING: This may expose sensitive information in the
      log.
      Default: false
    -xc, --expanded-coverage
      Enable Expanded Coverage analysis.
      Default: false
    -w, --fail-on-policy-warnings
      Fail on policy evaluation warnings
      Default: false
    -h, --help
      Show this help screen
      Default: false
    -e, --ignore-system-errors
      Ignore system errors (IO, network, server, etc)
      Default: false
    --pki-authentication
      Delegate to the JVM for PKI authentication
      Default: false
    -p, --proxy
      Proxy to use, format <host[:port]>. If unspecified, the operating system
      will be queried for the proxy settings
    -U, --proxy-user
      Credentials to use for proxy, format <username:password>
    -r, --result-file
      Path to a JSON file where the results of the policy evaluation will be
      stored in a machine-readable format
    -s, --server-url
      URL to the IQ Server to which the scan result should be uploaded
    -t, --stage
      The stage to run analysis against. Accepted values:
      develop|build|stage-release|release|operate
      Default: build
