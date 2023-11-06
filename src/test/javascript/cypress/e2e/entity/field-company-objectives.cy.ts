import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('FieldCompanyObjectives e2e test', () => {
  const fieldCompanyObjectivesPageUrl = '/field-company-objectives';
  const fieldCompanyObjectivesPageUrlPattern = new RegExp('/field-company-objectives(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const fieldCompanyObjectivesSample = {};

  let fieldCompanyObjectives;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/field-company-objectives+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/field-company-objectives').as('postEntityRequest');
    cy.intercept('DELETE', '/api/field-company-objectives/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (fieldCompanyObjectives) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/field-company-objectives/${fieldCompanyObjectives.id}`,
      }).then(() => {
        fieldCompanyObjectives = undefined;
      });
    }
  });

  it('FieldCompanyObjectives menu should load FieldCompanyObjectives page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('field-company-objectives');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FieldCompanyObjectives').should('exist');
    cy.url().should('match', fieldCompanyObjectivesPageUrlPattern);
  });

  describe('FieldCompanyObjectives page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(fieldCompanyObjectivesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FieldCompanyObjectives page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/field-company-objectives/new$'));
        cy.getEntityCreateUpdateHeading('FieldCompanyObjectives');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldCompanyObjectivesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/field-company-objectives',
          body: fieldCompanyObjectivesSample,
        }).then(({ body }) => {
          fieldCompanyObjectives = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/field-company-objectives+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/field-company-objectives?page=0&size=20>; rel="last",<http://localhost/api/field-company-objectives?page=0&size=20>; rel="first"',
              },
              body: [fieldCompanyObjectives],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(fieldCompanyObjectivesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details FieldCompanyObjectives page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('fieldCompanyObjectives');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldCompanyObjectivesPageUrlPattern);
      });

      it('edit button click should load edit FieldCompanyObjectives page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldCompanyObjectives');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldCompanyObjectivesPageUrlPattern);
      });

      it('edit button click should load edit FieldCompanyObjectives page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldCompanyObjectives');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldCompanyObjectivesPageUrlPattern);
      });

      it('last delete button click should delete instance of FieldCompanyObjectives', () => {
        cy.intercept('GET', '/api/field-company-objectives/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('fieldCompanyObjectives').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldCompanyObjectivesPageUrlPattern);

        fieldCompanyObjectives = undefined;
      });
    });
  });

  describe('new FieldCompanyObjectives page', () => {
    beforeEach(() => {
      cy.visit(`${fieldCompanyObjectivesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FieldCompanyObjectives');
    });

    it('should create an instance of FieldCompanyObjectives', () => {
      cy.get(`[data-cy="companyObjectives"]`).type('payment').should('have.value', 'payment');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        fieldCompanyObjectives = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', fieldCompanyObjectivesPageUrlPattern);
    });
  });
});
