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

* `main` branch uploads the generated artifact as follows:
  * `s3://harbr-deploy-artifacts/trino-snowflake-connector/latest/trino-snowflake-367.zip`
  * `s3://harbr-deploy-artifacts/trino-snowflake-connector/main/trino-snowflake-367.zip`
  * `s3://harbr-deploy-artifacts/trino-snowflake-connector/main/trino-snowflake-367-${VERSION}.zip` (where `VERSION` is the value of semantic version).
  Please note, semantic version updates the value `VERSION` after uploading it.

`s3://harbr-deploy-artifacts/trino-snowflake-connector/latest/trino-snowflake-367.zip` will be used by `spaces_core`
and `gcp-remote-installer` to check the artifact and deploy it to AWS/GCP respectively.

Slack notifications will trigger for `#build-remote-services-gcp` and `#build-remote-services-aws` channels.

Semantic release runs automatically on `main` branch Deploys to dev by pushing the Zip file to the right 