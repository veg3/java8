package day02.java8;

import java.util.Optional;

public class NewMan {
	private Optional<Godness> godness = Optional.empty();

	public Optional<Godness> getGodness() {
		return godness;
	}

	public void setGodness(Optional<Godness> godness) {
		this.godness = godness;
	}

	@Override
	public String toString() {
		return "NewMan [godness=" + godness + "]";
	}

	public NewMan(Optional<Godness> godness) {
		super();
		this.godness = godness;
	}

	public NewMan() {
		super();
	}
    
}
