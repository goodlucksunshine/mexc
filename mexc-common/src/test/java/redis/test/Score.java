package redis.test;

import java.io.Serializable;

public class Score implements Serializable{

	private String name;
	
	private Integer score;

	public Score(String name, Integer score){
		
		this.name = name;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Score [name=" + name + ", score=" + score + "]";
	}

	

	
}
