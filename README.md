# Java-fluid-level-monitoring-app
Java swing GUI desktop app for fluid level monitoring

![image](https://user-images.githubusercontent.com/72305802/222460045-74667f93-bfc1-4bd5-a3ae-d8d2d7b8f2b8.png)

This is a fluid level monitoring app. App assumes the tank with fluid has two input valves and one output valve. Each valve has a flow value in [l/min], that can be specified:

![image](https://user-images.githubusercontent.com/72305802/222460459-9aba99a2-8cf6-41a6-8b4b-0f3631f507cf.png)

Based on stream flow values app recalculates fluid height in tank in [m]. 

Assuming the tank is a cylindrical one, with radius specified:

![image](https://user-images.githubusercontent.com/72305802/222461272-0491c506-3454-4ffd-9412-d8ab5d5ff7df.png)

App stores historic fluid level records and calculates statistics from it:

![image](https://user-images.githubusercontent.com/72305802/222462312-9df4ea21-fffb-4a53-bd24-6d7c936e17e8.png)

All records can be viewed as a table:

![image](https://user-images.githubusercontent.com/72305802/222462836-44061c4c-b595-4b38-ab27-10f64e6249f1.png)
