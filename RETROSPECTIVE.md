# Retrospective Analysis

## Problems

1. For iteration 2, setting up the database was the biggest point of concern for our group. (Bitspace)

2. Communicating our progress on the parts that we were assigned to was another problem. (Meatspace)

## Background

### Database

While all of us do have a basic understanding of SQL queries, we do not have any practical knowledge about setting up a database. The issue was that the amount of work required to set up HSQLDB was too much for one person alone. HQSLDB was hard to set up because the error messages did not provide very much info about the issue, so a lot of time was spent trying to figure out what went wrong. 

Looking back at iteration 1, we had one person create the database stubs. From our discussions before and after that iteration, it seemed like one person was enough to get the job done. Moving forward onto iteration 2, we decided to have the same person continue with managing everything related to databases. This ended up being a mistake because we were unable to create HSQLDB implementations for all of our features/user stores. Some of them only had stub implementations available.

As a group, we ended up staying up late on the night before the due date troubleshooting errors and working as a team to get things working. It was that night where we realized that the work required to set up the database was a lot more than what one person should handle. Having more people to bounce ideas off of made troubleshooting errors much easier.

### Communication

After further discussions about iteration 2, we agreed that another issue contributed to the problems we had with our database. There was not enough communication about how things were going with our respective parts. In our group chat, there would be days where no one would say anything at all. No updates about progress meant that no one had a clue if they were struggling with something or needed more help. This was particularly troublesome in the days before the iteration due date where we didn't even know if people were even working on their parts at all.

In fact, because there was not enough communication, we didn't even know one of our members had dropped the course. While it's unfortunate the member did not let us know they had dropped the course, if we had enforced regular updates, we could have been aware of this much earlier. Then, after a certain amount of time had passed, we could have decided on a course of action and distributed the work earlier. Instead, we wrongly assumed everyone was working on their parts and that there were no issues which left us scrambling to get things done at the end.

## Moving Forward

To fix the database issues, we have decided to have multiple people work on the database. As a measurement of success, all the HSQLDB implementations needed for our code should be working by the end of the 3rd iteration.

To fix the communication issues we have decided to conduct weekly Discord meetings every Monday on the progress of our code. This will allow us to uncover problems earlier with the added benefit of giving everyone a general idea about the state of the project. With more empasis on communication and finding out problems earlier, as a measurement of success, if we are able to receive less code smells than the combined average of Iteration 1 and Iteration 2, this change will be a success.