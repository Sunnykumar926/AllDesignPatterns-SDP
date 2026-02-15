#include<iostream>
#include<mutex>

// static mutex means only one mutex object exists and that is shared 
// across all instance 

using namespace std;

class Singleton{
private:
    static Singleton* instance;
    static mutex mtx;

    Singleton(){
        cout<<"Singleton constructor called"<<endl;
    }
public:
    static Singleton* getInstance(){
        if(instance==nullptr){
            lock_guard<mutex> lock(mtx); 
            // locks the mutex automatically when created 
            // and unlock automatically when it goes out of scope
            if(instance==nullptr){
                instance = new Singleton();
            }
        }
        return instance;
    }
};

// Initialization of static  memebers
Singleton* Singleton::instance=nullptr;
mutex Singleton::mtx;

int main(){
    Singleton* s1 = Singleton::getInstance();
    Singleton* s2 = Singleton::getInstance();

    cout<< (s1 == s2) << endl;
}




