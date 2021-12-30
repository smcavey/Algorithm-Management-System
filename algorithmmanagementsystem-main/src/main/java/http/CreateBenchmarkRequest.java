package http;

public class CreateBenchmarkRequest {
	
	public String name;
	public String implementation;
	public String l1cache;
	public String l2cache;
	public String l3cache;
	public String manufacturer;
	public String ram;
	public int cores;
	public int threads;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getl1cache( ) { return l1cache; }
	public void setl1cache(String l1cache) { this.l1cache = l1cache; }
	
	public String getl2cache( ) { return l2cache; }
	public void setl2cache(String l2cache) { this.l2cache = l2cache; }
	
	public String getl3cache( ) { return l3cache; }
	public void setl3cache(String l3cache) { this.l3cache = l3cache; }
	
	public String getManufacturer( ) { return manufacturer; }
	public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
	
	public String getRam( ) { return ram; }
	public void setRam(String ram) { this.ram = ram; }
	
	public int getCores( ) { return cores; }
	public void setCores(int cores) { this.cores = cores; }
	
	public int getThreads( ) { return threads; }
	public void setThreads(int threads) { this.threads = threads; }
	
	public CreateBenchmarkRequest() {
	}
	
	public CreateBenchmarkRequest (String name, String l1cache, String l2cache, String l3cache, String ram, int threads, int cores, String manufacturer, String implementation) {
		this.name = name;
		this.l1cache = l1cache;
		this.l2cache = l2cache;
		this.l3cache = l3cache;
		this.ram = ram;
		this.threads = threads;
		this.cores = cores;
		this.manufacturer = manufacturer;
		this.implementation = implementation;
	}
	
	public String toString() {
		return "CreateBenchmark(" + name + ", " + implementation + ", " + l1cache + ", " + ", " + l2cache + ", " + l3cache + ", " +
				manufacturer + ", " + ram + ", " + cores + ", " + threads + ")";
	}

}