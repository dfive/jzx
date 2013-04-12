package pt.ist.ave.jzx.Instructions;

public class ADD_HLSP extends Instruction {

	public ADD_HLSP(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		_cpu.add_hl(_cpu.getM_sp16());
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 11;
	}

}
