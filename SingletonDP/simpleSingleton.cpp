#include<iostream>
using namespace std;

class Singleton{
private:
    static Singleton* instance=nullptr; // => this is only declaration not memory allocation
    Singleton(){
        cout << "Singleton Constructor called"<<endl;
    }

public:
    static Singleton* getInstance(){
        if(instance==nullptr){
            instance = new Singleton();
        }
        return instance;
    }
};

// for static members, C++ requires => One seprate definition outside the class
Singleton* Singleton::instance = nullptr;

int main(){
    Singleton* s1= Singleton::getInstance();
    Singleton* s2= Singleton::getInstance();

    cout<<(s1 == s2)<<endl;
    return 0;
}