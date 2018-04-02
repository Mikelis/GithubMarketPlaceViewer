# GithubMarketPlaceViewer
Android Application that uses the github GraphQL API to pull the marketplace Data about the newest Github apps. 

Application consists from 8 packages and 1 activity.
QueryManager does the communication with the API server.
AppsListFragment is the main app UI, that shows GitHub apps and gives a possibility to filter data.

okgraphql is used to request data from API, GSON is used to cast JSON response to POJO. Greenrobot Eventbus is used for intercommunication between application parts. RxJava is used for threading and response merging. 
