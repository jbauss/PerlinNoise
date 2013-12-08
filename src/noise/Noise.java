package noise;

public class Noise {
	
	private int seed;
	
	public Noise(){
		this.seed = 0;
	}
	
	public Noise(int seed) {
		this.seed = seed;
	}
	
	public float noise(int x) {
		int n = x + seed;
		n = ( n << 13 ) ^ n;
		float rand = 1 - ( (n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824f;
		return rand;
	}
	
	public float noise(int x, int y) {
		int n = x + y * 57 + seed;
		n = ( n << 13 ) ^ n;
		float rand = 1 - ( (n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824f;
		return rand;
	}
	
	public float noise(int x, int y, int z) {
		int n = x + y + z * 57 + seed;
		n = ( n << 13 ) ^ n;
		float rand = 1 - ( (n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824f;
		return rand;
	}
	
	public float smoothNoise(int x) {
		return smoothNoise(x, 0);
	}
	
	public float smoothNoise(int x, int y) {
		float corners = (noise(x+1, y+1) + noise(x+1, y-1) + noise(x-1, y+1) + noise(x-1, y-1)) / 16f;
		float sides = (noise(x, y+1) + noise(x, y-1) + noise(x-1, y) + noise(x+1, y)) / 8f;
		float center = noise(x, y) / 4f;
		
		return corners + sides + center;
	}
	
	public float interpolatedNoise(float x) {
		int integerX = (int) x;
		float fractionalX = x - integerX;
		
		float v1 = smoothNoise(integerX);
		float v2 = smoothNoise(integerX + 1);
		
		return interpolateCosine(v1, v2, fractionalX);
	}
	
	public float interpolatedNoise(float x, float y) {
		int integerX = (int)x;
		float fractionalX = x - integerX;
		
		int integerY = (int)y;
		float fractionalY = y - integerY;
		
		float v1 = smoothNoise(integerX, integerY);
		float v2 = smoothNoise(integerX + 1, integerY);
		float v3 = smoothNoise(integerX, integerY + 1);
		float v4 = smoothNoise(integerX + 1, integerY + 1);
		
		float i1 = interpolateCosine(v1, v2, fractionalX);
		float i2 = interpolateCosine(v3, v4, fractionalX);
		
		return interpolateCosine(i1, i2, fractionalY);
	}
	
	// x must be in the range [0,1]
	public float interpolateLinear(float a, float b, float x) {
		return a * (1 - x) + b * x;
	}
	
	// x must be in the range [0,1]
	public float interpolateCosine(float a, float b, float x) {
		float ft = x * (float)Math.PI;
		float f = (1 - ((float)Math.cos(ft))) * 0.5f;
		return a * (a - f) + b * f;
	}

}
