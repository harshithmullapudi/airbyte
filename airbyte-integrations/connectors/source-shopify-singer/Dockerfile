FROM airbyte/integration-base-singer:0.1.1

# Bash is installed for more convenient debugging.
RUN apt-get update && apt-get install -y bash gcc && rm -rf /var/lib/apt/lists/*

ENV CODE_PATH="source_shopify_singer"
ENV AIRBYTE_IMPL_MODULE="source_shopify_singer"
ENV AIRBYTE_IMPL_PATH="SourceShopifySinger"

WORKDIR /airbyte/integration_code
COPY $CODE_PATH ./$CODE_PATH
COPY main.py ./
COPY setup.py ./
RUN pip install tap-shopify-velocity==1.6.0
RUN pip install .

LABEL io.airbyte.version=0.2.2
LABEL io.airbyte.name=airbyte/source-shopify-singer

ENV AIRBYTE_ENTRYPOINT "python /airbyte/integration_code/main.py"
ENTRYPOINT ["python", "/airbyte/integration_code/main.py"]