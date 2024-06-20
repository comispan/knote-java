This project is based off https://learnk8s.io/spring-boot-kubernetes-guide.

I have included the following features:
  - request logging
  - logging traceId and spanId
  - Github action for automated build and publish image to DockerHub

To run dockerhub image:
  - docker-compose -f docker-compose-dockerhub.yml up -d