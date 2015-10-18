#include<bits/stdc++.h>
struct Data
{
    double time,lat,lon;
}datapoints[20000];

bool compare(Data l,Data r)
{
    return l.time<r.time;
}
bool check(char str[])
{
    int len = strlen(str);
    if(str[9] >= '6')        //day is weekday
        return true;
    else
        return false;
}
int getTime(char *str)
{
    int len = strlen(str);
    int hr,m,s;
    hr = (str[11] - '0')*10+(str[12]-'0');
    m = (str[14] - '0')*10+(str[15]-'0');
    //s = (str[16] - '0')*10+(str[17]-'0');
    return (hr * 60 + m);
}
using namespace std;
int main()
{
    freopen("out.txt","r",stdin);
    freopen("weekend.txt","w",stdout);
    int id,i;
    double lat,lon;
    char str[100];
    int M = 25000,k=0;
    while(M--)
    {
        cin>>id>>str>>lat>>lon;
       if(check(str))
       {
           k++;
           datapoints[k].time = getTime(str);
           datapoints[k].lat = lat;
           datapoints[k].lon = lon;
       }
    }
    sort(datapoints,datapoints+k,compare);
    for(i=0;i<k;i++)
    {
        cout<<datapoints[i].time<<" "<<datapoints[i].lat<<" "<<datapoints[i].lon<<"\n";
    }


	return 0;
}
