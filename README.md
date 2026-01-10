# The project's aim

Api lists all non-fork repositories of given user, informationvgiven in response is:
-repository name
-repository owner's login
-name and last commit's sha for every branch

when provided with non-existing user api returns 404 with appropriate message.

## Technologies

-Java 25
-Spring Boot 4.0.1
-Gradle-Kotlin

## How to use

In order to fetch repositories one needs to send GET request
/repos?username={username}
where '{username}' us username of github user whose repositories we want to fetch.

You need to send request with these 3 headers:
Accept
X-GitHub-Api-Version
Authorization
(Authorization is optional but without it your number of requests is limited)
you can find details about how to set up these headers here
https://docs.github.com/en/rest/branches/branches?apiVersion=2022-11-28