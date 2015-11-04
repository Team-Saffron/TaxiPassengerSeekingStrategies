#include<bits/stdc++.h>

using namespace std;

struct Data
{
    double time,lat,lon;
    Data()
    {
	}
	Data(double a,double b,double c)
	{
		time=a;lat=b;lon=c;
	}
}datapoints[20000],obj;


int main()
{
	int i,len,len_of_test_file;
	freopen("weekday.txt","r",stdin);
	freopen("testing_file_for_weekdays","w",stdout);
	i=0;
	while(cin>>datapoints[i].time)
	{
		cin>>datapoints[i].lat>>datapoints[i].lon;
		i++;
	}
	len=i;
	len_of_test_file=1000;
	//cout<<len_of_test_file<<"\n";
	for(i=0;i<len_of_test_file;i++)
	{
		obj=datapoints[rand()%len];
		obj.lat = (obj.lat)/(double)1000000.0 + 37.0;
		obj.lon = (-1.0*((obj.lon)/(double)1000000.0 + 122.0));
		cout.precision(6);
		cout<<fixed<<obj.time<<" "<<obj.lat<<" "<<obj.lon<<"\n";
	}
	return 0;
}
