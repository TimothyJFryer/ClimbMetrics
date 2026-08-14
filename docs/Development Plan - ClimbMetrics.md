Registration / Login

- User profile  
  - Grade range  
  - Preferred styles  
  - Experience  
  - Goals

    \-   Log climbs

- Grade  
  - Style  
  - Gym/location  
  - Date  
  - Attempts  
  - Send / flash / project / completed  
  - Notes  
      
- Progress tracking  
- View climb history  
- Search/filter climb history  
- Upload climbing videos  
- View uploaded videos  
- Basic movement analysis  
- Video comparison  
- Suggest improvements  
- Social Features

V1 initial features:

- Registration  
- Login/profile  
- Log climbs   
- Track attempts  
- view/filter history  
- Video upload  
- Progress statistics/grades/style

# Climb Metrics \- V1 Dev Plan

Version: 1.0  
Status: In development  
Last updated: 14/08/2026

1. ### Overview

The aim for this project is to develop a climbing web app that allows users to log their ascents and receive feedback and suggestions to improve. In future versions this will be done via video upload and AI video movement analysis.

Version 1 will primarily focus on the core features of the app, to progress it to a working web app with barebones features. This project will be used to demonstrate real-world software development best practices, such as git, docker, CI/CD, IaC (Cloudformation), and cloud deployment and monitoring on AWS.

2. ### Version 1 objectives

The primary objective of this first iteration is as followed:

- Allow users to securely create accounts  
- Allow users to securely access and manage accounts  
- Allow users to record climbs and attempts  
- Allow users to view and filter their ascent history  
- Allow users to upload videos and associate with a log  
- Provide some meaningful statistics about the users climbing performance  
- Create a scalable application architecture  
- Deploy to AWS  
- Implement automated testing  
- Provision infrastructure via IaC (CloudFormation)

3. ### Functionality Requirements

#### 3.1 Registration

Users should be able to create accounts using:

- Email  
- Password  
- Username

Requiring:

- Unique Email  
- No plaintext passwords  
- Invalid registration declined  
- Valid registration accepted  
- Appropriate validation  
- Error feedback on failure

#### 3.2 Login & Profile

Users should be able to:

- Login using their email/password  
- Login securely  
- Log out  
- View their profile  
- Update profile and login information

Requiring:

- Preventing unauthed users from accessing private data

#### 3.3 Climb Logging

Users should be able to record a climb with information such as:

- Grade  
- Style  
- Date  
- Location/gym  
- Number of attempts  
- Completion status  
- Notes

Example completion statuses:

- Flash  
- Send  
- Project  
- Attempted  
- Failed

Requiring:

- associating each climb with the relevant authenticated user user.

#### 3.4 Attempt tracking

Users should be able to record individual attempts per climb.

Attempts may contain:

- Attempt number  
- Result  
- Date/time  
- Notes  
- Optional video

#### 3.5 Log book

Users should be able to view and filter their previous completed and attempted ascents.

Filtering should include:

- Grade  
- Style  
- Date  
- Location  
- Completion status

Requiring:

- Only relevance users climb displayed

#### 3.6 Video upload

Users should be able to optionally upload a video to associate with a climb

Requiring:

- Upload validation  
- Restrict file types  
- Apply file size limits  
- Store videos separately from app data  
- Associate to relevant user & climb  
- Prevent unauth users from accessing private videos


Not requiring in V1:

- Video analysis  
- AI integration

#### 3.7 Progress statistics

Users should be provided with sensible statistics about their climbing progress including:

- Highest grade climbed  
- Number of climbs  
- Number of attempts  
- Number of successful climbs  
- Flash rate  
- Send rate  
- Grade distribution  
- Performance by climbing style  
- Progress over time

(Subject to change)

Requiring:

- Data is collected from users climbing data (not entered manually)

### 4\. Non-Functional Requirements

#### Performance

The application should provide responsive interactions for normal user operations.

Large operations such as video uploads should not unnecessarily block the main application.

#### Security

The application should follow security best practices including:

- Password hashing  
- Secure authentication  
- Authorisation checks  
- Input validation  
- HTTPS in production  
- Secure handling of secrets  
- Least-privilege AWS IAM permissions  
- Appropriate database access controls  
- Secure video storage

#### Reliability

The application should handle expected failures gracefully.

Examples:

- Failed login  
- Invalid form data  
- Failed database request  
- Failed video upload  
- Expired authentication

#### Maintainability

The codebase should:

- Follow a consistent project structure.  
- Use meaningful naming.  
- Avoid unnecessary duplication.  
- Include automated tests.  
- Use Git-based version control.  
- Document significant architectural decisions.

### 5\. Tech stack

#### Frontend

- React  
- Vite  
- Typescript

#### Backend

- Springboot  
- REST API

#### Database

- PostgreSQL

#### Development

- Git  
- Docker

#### Cloud

- AWS

Potential AWS services:

- Aurora Postgres serverless  
- S3 \- video object store  
- Lambda container image or ECS with Fargate  
- SQS \- decouple  
- Cloudfront distribution  
- IAM, KMS, Secrets manager, Parameter store \- access and secrets  
- Cloudwatch \- monitor  
- WAF, Network firewall, SGs, NACL \- security  
- Cloudformation \- IaC

The final AWS architecture will be determined based on the application's requirements rather than forcing the use of unnecessary services. 

### 	6\. General development methodology

Despite this being an individual project, it is intended to replicate a professional development environment as best it can. Therefore each feature should follow:

Requirement \-\> Issue \-\> Feature branch \-\> Implementation \-\> Automated tests \-\> Pull request \-\> CI checks \-\> (self) review \-\> Merge

Initial development had focused on validating the functionality of the core application and creating the basic tech stack. As it has progressed, the process is being formalised to ensure a more structured maintainable project.

This transition will involve introducing consistent code organisation, separation of concerns, automated testing, improved version-controlled infrastructure, and CI/CD practices. 

### 	7\. Development Stages

#### Phase 1 \- Project setup

Objectives:

- Create github repo  
- Establish project structure  
- Configure frontend  
- Configure backend  
- Configure database  
- Create initial document

Deliverables:

- Working frontend  
- Working backend  
- Database connection  
- Readme

#### Phase 2 \- Authentication 

Objectives:

- Registration  
- Login  
- Logout  
- Authentication  
- User profile  
- Authorisation

Testing:

- Valid registration \- Successful registration  
- Invalid registration \- Denied  
- Duplicate email \- Denied  
- Valid login  
- Invalid password \- Denied  
- Unknown user  
- Unauthenticated request  
- Authorised requests

#### Phase 3 \- Climb logging

Objectives:

- Create climb  
- Edit climb  
- Delete climb  
- Record climb info (grade,style,location,date,completion status)  
- Add notes  
- Add attempts  
- Decrease attempts (if accidentally added)

Testing:

- Valid climb creation  
- Invalid data  
- Editing climb  
- Deleting climb  
- User ownership  
- Unauthed acces

#### Phase 4 \- Climbing history

Objectives:

- History page  
- Pagination  
- Filtering  
- Sorting  
- Searching

Testing:

- Correct climb displayed  
- Filters  
- Sorts  
- Empty results  
- Unathed acces

#### Phase 5 \- Video upload

Objectives:

- Video upload  
- File validation  
- Size restrictions  
- Storage  
- Video to attempt association  
- Video playback

Testing:

- Valid upload  
- Invalid file type  
- Oversized file  
- Failed upload  
- Unauthorised access  
- Correct video association

#### Phase 6 \- Progress statistics

Objectives:

- Grade statistics  
- Style statistics  
- Total Climbs  
- Total/Average attempts  
- Successful climbs  
- Flash/Climb rate  
- Progress over time

Testing:

- Verify statistics against known datasets

### 	8\. Testing strategy

Testing will occur through development rather than being left to the end. It will consist of unit tests, that focus on individual piece of app logic, and integration test, that focus on the interaction between components.

Unit test examples:

- Statistics calculations  
- Validation  
- Utility functions

Integration test examples:

- API \-\> service \-\> DB  
- Authentication  
- DB operations

### 	9\. CI/CD and Dev plan

Once a sufficient test suite exists, GitHub Actions will be introduced.

#### Pull request

Pull Request \-\> Built \-\> Unit tests \-\> Integration Tests \-\> Security/Dependancy checks

A pull request should not be merged if required checks fail.

#### Production Deployment

After CI has been established:

Merge to main \-\> CI \-\> Build \-\> Deploy \-\> AWS

Deploy: Local \-\> Staging \-\> AWS  
    

The exact deployment strategy will be determined once the AWS architecture is finalised.

### 14\. V1 Definition of Done

V1 will be considered complete when:

- [ ] Users can register.  
- [ ] Users can log in and out.  
- [ ] Users can manage their profile.  
- [ ] Users can log climbs.  
- [ ] Users can track attempts.  
- [ ] Users can view and filter their history.  
- [ ] Users can upload climbing videos.  
- [ ] Users can view their progress statistics.  
- [ ] Appropriate authentication/authorisation is implemented.  
- [ ] Automated tests cover critical functionality.  
- [ ] CI runs automatically on pull requests.  
- [ ] Application is deployed to AWS.  
- [ ] Infrastructure is reproducible using Terraform.  
- [ ] Production secrets are not stored in Git.  
- [ ] Application logging/monitoring is configured.  
- [ ] Documentation explains how to run and deploy the application.

