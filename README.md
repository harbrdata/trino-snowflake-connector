# trino-snowflake
Trino Snowflake Connector

To complile: `docker run -it  -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.8.6-openjdk-11  mvn -B package --file pom.xml`

# Pipeline

The command above is executed on any branch, the generated artifact will appear in the CircleCI job.

* `dev` branch: 
  * **GCP**: Pushes the generated artifact to the right location on [`gcp-remote-installer`](https://github.com/harbrdata/gcp-remote-installer)
`dev` branch, then [its pipeline](https://app.circleci.com/pipelines/github/harbrdata/gcp-remote-installer?branch=dev) takes care of it and deploys it to Dev.
  * **AWS**: Pushes the generated artifact to the right location on [`spaces_core`](https://github.com/harbrdata/spaces_core) `dev` branch.
At the moment, this won't deploy to dev.

* `main` branch:
  * **GCP**: Pushes the generated artifact to the right location on [`gcp-remote-installer`](https://github.com/harbrdata/gcp-remote-installer)
  `trino-snowflake-connector` branch, raises a PR on that repo to be reviewed. Once merged, it will get deployed into UAT.
  * **AWS**: Pushes the generated artifact to the right location on [`spaces_core`](https://github.com/harbrdata/spaces_core) `trino-snowflake-connector` branch, raises a PR on that repo to be reviewed.
At the moment, this won't deploy to dev.

Slack notifications will trigger for `#build-remote-services-gcp` and `#build-remote-services-aws` channels.

Semantic release runs automatically on `main` branch Deploys to dev by pushing the Zip file to the right 