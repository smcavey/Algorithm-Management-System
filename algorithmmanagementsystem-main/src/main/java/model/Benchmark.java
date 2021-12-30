package model;

public class Benchmark {
	
	public String name;
	public String implementation; // filename of the implementation
	public String l1cache;
	public String l2cache;
	public String l3cache;
	public int cores;
	public int threads;
	public String manufacturer;
	public String ram;
	
	public Benchmark(String name, String l1cache, String l2cache, String l3cache, String ram, int threads, int cores, String manufacturer, String implementation) {
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
	
	public Benchmark(String name) {
		this.name = name;
	}
}
