public class Profile {
    private String name;
    private int age;
    private int birthday;
    private String school;

    public Profile(String n, int a){
        this.name = n;
        this.age = a;
    }

    public Profile(String n, int a, int b, String s){
        this.name = n;
        this.age = a;
        this.birthday = b;
        this.school = s;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public int getBirthday() {
        return this.birthday;
    }

    public String getSchool() {
        return this.school;
    }

    @Override
    public boolean equals(Object obj) {
        Profile castedObj = (Profile) obj;

        boolean nameEq = (this.name.equals(castedObj.getName()));
        boolean ageEq = (this.age == castedObj.getAge());
        boolean birthEq = (this.birthday == castedObj.getBirthday());
        boolean schoolEq = true;
        if(this.school != null){
            schoolEq = (this.school.equals(castedObj.getSchool()));
        }
        return nameEq && ageEq && birthEq && schoolEq;
    }
}
