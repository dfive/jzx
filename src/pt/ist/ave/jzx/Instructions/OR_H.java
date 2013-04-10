package pt.ist.ave.jzx.Instructions;

public class OR_H extends Instruction {

	public OR_H(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.or_a(_cpu.getM_h8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
