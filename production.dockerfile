FROM amazoncorretto:11
ENV PORT=80
ENV LANG=C.UTF-8 \
    LC_ALL=C.UTF-8
VOLUME /tmp
ADD target/app.jar app.jar
RUN mkdir -p /aws; \
    yum update -y \
    && yum install -y \
    groff \
    less \
    python \
    python-pip \
    jq \
    && pip install awscli \
    && yum clean all
RUN bash -c 'touch /app.jar'
COPY docker/entrypoint.sh /entrypoint.sh
RUN chmod 755 /entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]
