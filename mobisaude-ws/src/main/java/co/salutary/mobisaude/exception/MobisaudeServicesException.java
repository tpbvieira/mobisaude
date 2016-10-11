package co.salutary.mobisaude.exception;

/**
 * Classe que representa uma excecao relacionado a seguranca.
 *
 */
public class MobisaudeServicesException extends Exception {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7101456306750927309L;
	/**
	 * Construtor
	 * @param mensagem
	 */
	public MobisaudeServicesException(String mensagem) {
		super(mensagem);
	}
	/**
	 * Construtor
	 * @param mensagem
	 * @param causa
	 */
	public MobisaudeServicesException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	/**
	 * Construtor
	 * @param causa
	 */
	public MobisaudeServicesException(Throwable causa) {
		super(causa);
	}
}
