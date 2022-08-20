#include <iostream>
using namespace std;
#include <vector>
class MyString {
	vector<char> thestring;
	vector<char>::iterator fi;
public:

	bool Pal() {
		int start = 0, end;
		RemoveSpace();
		UpCase();
		end = thestring.size()-1;
		while(start<=end)
			if(thestring[start]==thestring[end]){
				start++;
				end--;
			}
			else
			{
				return false;
			}
		return true;
	}
	void RemoveSpace(){
		
		for(fi = thestring.begin(); fi != thestring.end();++fi)
			if(*fi == ' ')
				thestring.erase(fi);
	}
	void UpCase() {
		for(fi = thestring.begin(); fi != thestring.end();++fi)
			if(*fi>='a' && *fi<='z')
				*fi = *fi - 32;
	}
	void GetString(){
		char c;
		cout << "Enter a string:";
		cin >> c;
		while (c != '\n'){
			thestring.push_back(c);
			cin.get(c);
		}
	}
	
	void WriteString() {
		for(fi = thestring.begin(); fi != thestring.end();++fi)
			cout << *fi;
	}
};

int main () {
	MyString s;
	s.GetString();
	if(s.Pal()) 
		cout << "Palindrome";
	else
		cout << "Not a palindrome";
	cout << endl;
	return 0;
}

