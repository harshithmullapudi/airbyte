FROM airbyte/integration-base-singer:0.1.1

RUN apt-get update && apt-get install -y jq curl bash && rm -rf /var/lib/apt/lists/*

ENV CODE_PATH="source_google_adwords_singer"
ENV AIRBYTE_IMPL_MODULE="source_google_adwords_singer"
ENV AIRBYTE_IMPL_PATH="SourceGoogleAdwordsSinger"

WORKDIR /airbyte/integration_code
COPY $CODE_PATH ./$CODE_PATH
COPY setup.py ./
COPY main.py ./
RUN pip install tap-adwords-velocity==1.16.0
RUN pip install .

LABEL io.airbyte.version=0.2.2
LABEL io.airbyte.name=airbyte/source-google-adwords-singer

ENV AIRBYTE_ENTRYPOINT "python /airbyte/integration_code/main.py"
ENTRYPOINT ["python", "/airbyte/integration_code/main.py"]