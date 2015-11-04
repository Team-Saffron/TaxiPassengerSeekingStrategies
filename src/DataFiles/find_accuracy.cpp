#include<bits/stdc++.h>

using namespace std;

struct node
{
	double time,dis;
}datapoint[2007];
int main()
{
	double time_threshold,dis_threshold,faulty,accuracy,time,dis;
	int i,counter,tc;
	cout<<"Enter the number of test cases : ";
	cin>>tc;
	fstream fin;
	fin.open("testResults.txt");
	for(i=1;i<=tc;i++)
	fin>>counter>>datapoint[i].time>>datapoint[i].dis;
	fin.close();
	counter=5;
	while(counter--)
	{
	    cout<<"\nEnter time threshold (in minutes) : ";
	    cin>>time_threshold;
	    cout<<"\nEnter distance threshod (in metres) : ";
	    cin>>dis_threshold;
	    faulty=0;
	    for(i=1;i<=tc;i++)
	    {
		    time=datapoint[i].time;
		    dis=datapoint[i].dis;
		    if(time > time_threshold && dis > dis_threshold)
		    {
			    faulty++;
		    }
	    }
	    accuracy = ((tc-faulty)/tc)*100.0;
	    cout<<"\naccuracy for given threshold : "<<accuracy<<"%\n";
    }
	return 0;
}
