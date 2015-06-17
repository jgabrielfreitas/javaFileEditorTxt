package objects;

public class Person {

	private String personType; // tipo de pessoa: 0 aluno, 1 professor, 2 diretor, 3 funcionario
	private int age; // idade
	private String name; // nome
	private double register; // matricula
	private String createAt; // data que foi criado
	
	public String getPersonType() {
		return personType;
	}

	public Person(String personType, int age, String name, double register) {
		this.personType = personType;
		this.age = age;
		this.name = name;
		this.register = register;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRegister() {
		return register;
	}

	public void setRegister(double register) {
		this.register = register;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	
	public String toString() {
		return personType + ":" + age + ":" + name + ":" + register + ":" + createAt;
	}
}
