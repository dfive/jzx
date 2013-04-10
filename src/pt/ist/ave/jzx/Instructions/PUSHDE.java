package pt.ist.ave.jzx.Instructions;

public class PUSHDE extends Instruction {

	public PUSHDE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.push(_cpu.de16());
	}

	@Override
	public int incTstates() {
		return 11;
	}

}
