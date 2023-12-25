 **Pet Store Project**

 1. **Running the app**:
     **prerequisites:**
      --> Have Docker installed and running and have Port 8080 and 3306 open

    **There are two ways to run the app**
      i. Fully containerized app
     ii. App running locally with containerized db


    **Steps to run the app in Docker**
      1. Clone the repo
      2. Navigate to source root
      3. Run the docker-compose command `docker-compose -f docker-compose.container.yml up` this should build the image and create a new db and users
      4. App will be running on port `8080` and db on port `3306`
   
    **Steps to run the app locally with db running on Docker**
      1. Clone the repo
      2. Import project as a Maven project into IDE of choice
      3. Run the app from IDE and it should create the DB Schema 
      4. If you run into issues run the following command from the project root(after the DB container starts) to create the DB schema and users manually.
           `docker exec -i pet-store-db sh -c 'mysql -h db --user=root --password=password' < src/main/resources/create_db_and_users.sql`
   **To shut down container(s)**

      --> Run `docker-compose -f docker-compose.container.yml down` to shut down the containers and remove it. 

    

    


                                                                        


