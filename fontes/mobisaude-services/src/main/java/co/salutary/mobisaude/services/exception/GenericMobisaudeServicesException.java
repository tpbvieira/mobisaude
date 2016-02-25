package co.salutary.mobisaude.services.exception;

/**
 * Classe que representa uma excecao relacionado a seguranca.
 *
 */
public class GenericMobisaudeServicesException extends Exception {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7101456306750927309L;
	/**
	 * Construtor
	 * @param mensagem
	 */
	public GenericMobisaudeServicesException(String mensagem) {
		super(mensagem);
	}
	/**
	 * Construtor
	 * @param mensagem
	 * @param causa
	 */
	public GenericMobisaudeServicesException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	/**
	 * Construtor
	 * @param causa
	 */
	public GenericMobisaudeServicesException(Throwable causa) {
		super(causa);
	}
}
