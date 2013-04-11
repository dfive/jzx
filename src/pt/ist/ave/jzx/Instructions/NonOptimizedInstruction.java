package pt.ist.ave.jzx.Instructions;

/***
 * 
 * @author ist166950
 * Class that implements the switch case for the non-implemented instructions.
 */
public class NonOptimizedInstruction extends Instruction {

	private int _tstates;
	public NonOptimizedInstruction(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {

	}

	@Override
	public int incTstates() {
		return _tstates;
	}

}
