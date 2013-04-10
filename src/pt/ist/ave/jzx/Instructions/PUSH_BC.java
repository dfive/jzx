package pt.ist.ave.jzx.Instructions;

public class PUSH_BC extends Instruction {

	public PUSH_BC(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.push(_cpu.bc16());
		}

	@Override
	public int incTstates() {
		return 11;
	}

}
