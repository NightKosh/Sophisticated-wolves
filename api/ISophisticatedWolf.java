package sophisticated_wolves.api;

public interface ISophisticatedWolf {

    /*
     * determine species based on biome
     */
    public int setSpecies();

    /*
     * reads data for species
     */
    public int getSpecies();

    /*
     * modifies data for species
     */
    public void updateSpecies(int species);
}
