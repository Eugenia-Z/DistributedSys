Stateful vs. stateless

Stateful:

- Retains information about the client across multiple request.
- Session infomation stored in memory or database.
- Client context: the server remembers the client's state between requests

Pro:

- Better suited for real-time applications or scenarios requiring context persistence

Con:

- Harder to scale horizontally, the client needs to be consistently connected to the same server
- Requires mechanisms for state replication and synchronization when scaling.

Example:

- A web application that requires user authentication and maintains session data for personalized content. (e-Com app)
- multiplayer games
- chat apps

Stateless:

- No client Context: every request must carry all necesaary information (authentication toekn, session data)
- Scalable: Easier to scale horizontally because requests are independent and can be processed by any instance of the service

Pro:

- simpler design and easier fault tolerance
- Supports better scalability and load balancing

Cons:

- Requires client to send all relevant data with each request
- More difficiult toimplement complex workflows without external state management (databases or caches)

Example:

- RESTful API
- microservices (in general don't maintain session information )
- cloud services
